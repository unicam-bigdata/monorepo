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
        while(table.length > 0){
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
                    return {label: item.node.name, ...item.node.properties, id: item.node.properties[newNodeKey],  collapsed: true }
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
            const descendants = getAllDescendants(graphData.links, node.id);
            const newNodes = graphData.nodes.slice();
            newNodes.forEach((item, index) => {
                if (item.id == node.id) {
                    item.collapsed = true;
                }
                if (descendants.includes(item.id)) {
                    newNodes.splice(index, 1);
                }
            });

            const newLinks = graphData.links.filter((item) => ((!descendants.includes(item.source.id) && !descendants.includes(item.target.id))));

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
        d3Force = {(link) =>{d3.forceLink().distance(100)}}

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

        /* nodeThreeObject={(node) => {
            const nodeProperties = Object.keys(node).filter((key) => (key !== "childLinks" && key !== "index" && key !== "collapsed" && key !== "vx" && key !== "vz" && key !== "vy" && key !== "x" && key !== "y" && key !== "z" && key !== "id"));
            const table = document.createElement('table');
            table.border = true;
            const tableBorderColor = node.collapsed ? 'blue' : 'green';
            table.style.border = `5px solid ${tableBorderColor}`;
            table.style.backgroundColor = "white"
            table.className = "table-container";

            // Create table header
            var headerRow = table.insertRow();

            var headerCell1 = headerRow.insertCell(0);
            var headerCell2 = headerRow.insertCell(1);
            headerCell1.className = "node-header";
            headerCell2.className = "node-header";
            headerCell1.innerHTML = "Property";
            headerCell2.innerHTML = "Value";

            // Create rows
            for (var i = 0; i < nodeProperties.length; i++) {
                var row = table.insertRow();
                var cell1 = row.insertCell(0);
                var cell2 = row.insertCell(1);
                cell1.className = "node-body";
                cell2.className = "node-body";
                cell1.innerHTML = nodeProperties[i];
                cell2.innerHTML = node[nodeProperties[i]];
            }

            table.className = 'node-label';
            return new CSS2DObject(table);
        }} */

        onNodeClick={handleNodeClick}
    />;
};