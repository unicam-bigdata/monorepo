import { useState, useMemo, useCallback, React } from 'react';
import ForceGraph3D from 'react-force-graph-3d';
import SpriteText from "three-spritetext";

export const ExpandableGraph = ({ graphData }) => {
    const rootId = 0;

    const nodesById = useMemo(() => {
        const nodesById = Object.fromEntries(graphData.nodes.map(node => [node.id, node]));
        // link parent/children
        graphData.nodes.forEach(node => {
            node.collapsed = node.id !== rootId;
            node.childLinks = [];
        });
        graphData.links.forEach(link => nodesById[link.source].childLinks.push(link));

        return nodesById;
    }, [graphData]);

    const getPrunedTree = useCallback(() => {
        const visibleNodes = [];
        const visibleLinks = [];
        (function traverseTree(node = nodesById[rootId]) {
            visibleNodes.push(node);
            if (node.collapsed) return;
            visibleLinks.push(...node.childLinks);
            node.childLinks
                .map(link => ((typeof link.target) === 'object') ? link.target : nodesById[link.target]) // get child node
                .forEach(traverseTree);
        })();

        return { nodes: visibleNodes, links: visibleLinks };
    }, [nodesById]);

    const [prunedTree, setPrunedTree] = useState(getPrunedTree());

    const handleNodeClick = useCallback(node => {
        node.collapsed = !node.collapsed; // toggle collapse state
        setPrunedTree(getPrunedTree())
    }, []);
    
    return <ForceGraph3D
        backgroundColor={"#222222"}

        graphData={prunedTree}

        linkWidth={1}
        linkDirectionalParticles={3}
        linkDirectionalArrowLength={5}
        linkThreeObjectExtend={true}
        linkThreeObject={(link) => {
            console.log(link);
            const sprite = new SpriteText((typeof(link.source)=="object"?link.source.id:link.source) +"->"+ (typeof(link.target)=="object"?link.target.id:link.target));
            sprite.color = "darkgrey";
            sprite.textHeight = 10.0;
            return sprite;
        }}

        linkPositionUpdate={(sprite, { start, end }) => {
            const middlePos = Object.assign(...['x', 'y', 'z'].map(c => ({[c]: start[c] + (end[c] - start[c]) / 2})));

            Object.assign(sprite.position, middlePos);
        }}

        nodeOpacity={0.3}
        nodeColor={node => !node.childLinks.length ? 'purple' : node.collapsed ? 'blue' : 'yellow'}
        nodeThreeObjectExtend={true}
        nodeThreeObject={(node) => {
            const sprite = new SpriteText(node.id);
            sprite.color = "darkgrey";
            sprite.textHeight = 10.0;
            return sprite;
        }}

        onNodeClick={handleNodeClick}
    />;
};