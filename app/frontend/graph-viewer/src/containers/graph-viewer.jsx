import { ExpandableGraph } from "../components/expandable-graph";
import { genRandomTree } from "../graph";
export const GraphViewer = () => {
    return (
        <div>
            <ExpandableGraph graphData={genRandomTree(220, true)} />
        </div>
    )
}