import { ExpandableGraph } from "../components/expandableGraph";
import { genRandomTree } from "../components/graphData";
export const GraphViewer = () => {
    return (
        <div>
            <ExpandableGraph graphData={genRandomTree(220, true)} />
        </div>
    )
}