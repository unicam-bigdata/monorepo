import { useCallback, React, useContext } from 'react';
import ForceGraph3D from 'react-force-graph-3d';
import SpriteText from "three-spritetext";
import { CSS2DRenderer, CSS2DObject } from 'three/addons/renderers/CSS2DRenderer.js';
import { getRelatedNodes } from '../api/get-related-nodes';
import { AppContext } from '../context/app-context';
import { nodeToTable } from './nodeToTable';

export const ExpandableGraph = ({ graphData }) => {
    const extraRenderers = [new CSS2DRenderer()];
    const { identifiers, setData } = useContext(AppContext);

    const getAllDescendants = useCallback((data, targetId) => {
        const descendants = [];

        function findDescendants(currentId) {
            const children = data.filter(entry => entry.source.id === currentId);
            children.forEach(child => {
                descendants.push(child.target.id);
                findDescendants(child.target.id);
            });
        }

        findDescendants(targetId);
        return descendants;
    }, []);

    const handleNodeClick = useCallback(async (node) => {
        const table = document.getElementsByClassName("nodeTable");
        while (table.length > 0) {
            table[0].parentNode.removeChild(table[0]);
        }
        document.getElementById("sidePanel").append(nodeToTable(node));

        if (node.collapsed) {
            const key = identifiers?.find((item) => item.label === node.label).key;
            const result = await getRelatedNodes(node.label, key, node.id);

            if (result.data.length > 0) {
                const newNodes = result.data.filter((nodeItem) => {
                    let itemNotFound = true;
                    const newNodeKey = identifiers?.find((identifier) => identifier.label === nodeItem.node.name).key;
                    graphData.nodes.forEach((item) => {
                        if (item.id == nodeItem.node.properties[newNodeKey]) {
                            item = { ...nodeItem.node.properties };
                            itemNotFound = false;
                        }
                    });
                    return itemNotFound;
                }).map((item) => {
                    const newNodeKey = identifiers?.find((identifier) => identifier.label === item.node.name).key;
                    return { label: item.node.name, ...item.node.properties, id: item.node.properties[newNodeKey], collapsed: true }
                })

                const newLinks = result.data.map((item) => {
                    const newNodeKey = identifiers?.find((identifier) => identifier.label === item.node.name).key;
                    return { source: node.id, target: item.node.properties[newNodeKey], relationship: item.relationship }
                });

                graphData.nodes.forEach((item) => {
                    if (item.id == node.id) {
                        item.collapsed = false;
                    }
                });

                setData({ nodes: [...graphData.nodes, ...newNodes], links: [...graphData.links, ...newLinks] });
            }
        } else {
            let newLinks;
            let newNodes;
            try {
                const descendants = getAllDescendants(graphData.links, node.id);
                newNodes = graphData.nodes.slice();
                newNodes.forEach((item, index) => {
                    if (item.id == node.id) {
                        item.collapsed = true;
                    }
                    if (descendants.includes(item.id)) {
                        newNodes.splice(index, 1);
                    }
                });

                newLinks = graphData.links.filter((item) => ((!descendants.includes(item.source.id) && !descendants.includes(item.target.id))));

            } catch (exception) {
                newNodes = graphData.nodes.slice();
                newNodes.forEach((item, index) => {
                    if (item.id == node.id) {
                        item.collapsed = true;
                        newNodes.splice(index, 1);
                    }
                });

                newLinks = graphData.links.filter((item) => (item.source.id !== node.id && item.target.id !== node.id));
                console.log(newLinks);
            }
            setData({ nodes: newNodes, links: newLinks });


        }

    }, [graphData]);

    return <ForceGraph3D
        backgroundColor={"#222222"}
        extraRenderers={extraRenderers}
        graphData={graphData}
        linkWidth={1}
        linkDirectionalParticles={3}
        linkDirectionalArrowLength={5}
        linkDirectionalArrowColor={(link) => { link.color = "red" }}
        linkThreeObjectExtend={true}
        linkThreeObject={(link) => {
            const sprite = new SpriteText(link.relationship.name);
            sprite.color = "yellow";
            sprite.textHeight = 7.0;
            return sprite;
        }}
        linkPositionUpdate={(sprite, { start, end }) => {
            const middlePos = Object.assign(...['x', 'y', 'z'].map(c => ({ [c]: start[c] + (end[c] - start[c]) / 2 })));
            Object.assign(sprite.position, middlePos);
        }}
        d3Force={(link) => { d3.forceLink().distance(100) }}

        nodeOpacity={0}
        nodeThreeObjectExtend={true}
        nodeThreeObject={node => {
            const nodeProperties = Object.keys(node).filter((key) => (key !== "childLinks" && key !== "index" && key !== "collapsed" && key !== "vx" && key !== "vz" && key !== "vy" && key !== "x" && key !== "y" && key !== "z" && key !== "id"));
            const sprite = new SpriteText(node[nodeProperties[1]]);
            const color = node.collapsed ? '#6494ff' : '#64ff99';

            sprite.color = color;
            sprite.textHeight = 10;
            return sprite;
        }}
        onNodeClick={handleNodeClick}
    />;
};