import { ExpandableGraph } from "../components/expandableGraph";
import { genRandomTree } from "../components/graphData";
import { getNodes } from "../api/get-nodes";
import { useState, useEffect } from "react";
export const GraphViewer = () => {
    const [data, setData] = useState();


    useEffect(() => {
        const result = getNodes({ nodeName: "Person" }).then((result) => {
            console.log(result.data);
            const nodes = result.data.map((item) => ({ ...item }));
            const links = [];

            relationStructure = {
                nodes,
                links
            };
            setData(relationStructure);

        });
    }, [])

    return (
        <div>
            <ExpandableGraph graphData={data} />
        </div>
    )
}