import { ExpandableGraph } from "../components/expandableGraph";
import { getNodes } from "../api/get-nodes";
import { useState, useEffect, useContext } from "react";
import { getIdentifiers } from "../api/get-identifiers";
import { AppContext } from "../context/app-context";

export const GraphViewer = () => {
    const { setIdentifiers, data} = useContext(AppContext);

    useEffect(() => {
        getIdentifiers().then((result) => {
            setIdentifiers(result.data);
        })

    }, []);

    return (
        <div>
            {data && <ExpandableGraph graphData={data} />}

        </div>
    )
}