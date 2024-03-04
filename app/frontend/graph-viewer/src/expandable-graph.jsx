import { useState, useMemo, useCallback } from 'react';
import ForceGraph3D from 'react-force-graph-3d';

export const ExpandableGraph = ({ graphData }) => {
    const rootId = 0;
    console.log(graphData)

    const nodesById = useMemo(() => {
        const nodesById = Object.fromEntries(graphData.nodes.map(node => [node.id, node]));
        console.log(nodesById);
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
        console.log(nodesById);
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
    console.log(prunedTree);
    return <ForceGraph3D
        graphData={prunedTree}
        linkDirectionalParticles={2}
        nodeColor={node => !node.childLinks.length ? 'green' : node.collapsed ? 'red' : 'yellow'}
        onNodeClick={handleNodeClick}
    />;
};