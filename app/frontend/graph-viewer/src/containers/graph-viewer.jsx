import { ExpandableGraph } from "../components/expandableGraph";
import { getNodes } from "../api/get-nodes";
import { dataConfig } from "../data-config";
import { useState, useEffect } from "react";
import { CSS2DRenderer} from 'three/addons/renderers/CSS2DRenderer.js';
import { genRandomTree } from "../components/graphData";


export const GraphViewer = () => {
    const [data, setData] = useState();

    useEffect(() => {
        const result = getNodes({ nodeName: "Person" }).then((result) => {
            const nodes = result.data.map((item) => ({ ...item, id: item[dataConfig.Person] }));
            const links = [{ source: "1", target: "2" }, { source: "1", target: "3" }, { source: "2", target: "3" }, { source: "2", target: "5" }];

            const relationStructure = {
                nodes,
                links
            };
            setData(relationStructure);

        });
    }, [])

    return (
        <div>
            {data && <ExpandableGraph graphData={data}  />}

        </div>
    )
}