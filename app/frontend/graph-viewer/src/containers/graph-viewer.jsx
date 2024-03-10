import { ExpandableGraph } from "../components/expandableGraph";
import { getNodes } from "../api/get-nodes";
import { useState, useEffect, useContext } from "react";
import { getIdentifiers } from "../api/get-identifiers";
import { AppContext } from "../context/app-context";

export const GraphViewer = () => {
    const { identifiers, setIdentifiers, data, setData } = useContext(AppContext);

    useEffect(() => {
        getIdentifiers().then((result) => {
            setIdentifiers(result.data);
        })

    }, []);

    useEffect(() => {
        if (identifiers) {
            getNodes({ nodeName: "Person" }).then((result) => {
                const nodeLabel = "Person";
                const nodeKey = identifiers?.find((item) => item.label === nodeLabel).key;
                const nodes = result.data.map((item) => ({ label: nodeLabel, ...item, id: item[nodeKey], collapsed: true }));
                const links = [];

                const relationStructure = {
                    nodes,
                    links
                };
                setData(relationStructure);

            })
        }

    }, [identifiers]);

    return (
        <div>
            {data && <ExpandableGraph graphData={data} />}

        </div>
    )
}