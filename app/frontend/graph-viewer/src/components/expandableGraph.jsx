import { useState, useMemo, useCallback, React, useEffect } from 'react';
import ForceGraph3D from 'react-force-graph-3d';
import SpriteText from "three-spritetext";
import { CSS2DRenderer, CSS2DObject } from 'three/addons/renderers/CSS2DRenderer.js';
import { genTableImg } from './nodeToTable';
import * as THREE from 'three'

export const ExpandableGraph = ({ graphData }) => {
    const rootId = "1";
    const extraRenderers = [new CSS2DRenderer()];

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
        extraRenderers={extraRenderers}
        graphData={prunedTree}

        linkWidth={1}
        linkDirectionalParticles={3}
        linkDirectionalArrowLength={5}
        linkThreeObjectExtend={true}
        linkThreeObject={(link) => {
            const sprite = new SpriteText((typeof (link.source) == "object" ? link.source.id : link.source) + "->" + (typeof (link.target) == "object" ? link.target.id : link.target));
            sprite.color = "darkgrey";
            sprite.textHeight = 10.0;
            return sprite;
        }}
        linkPositionUpdate={(sprite, { start, end }) => {
            const middlePos = Object.assign(...['x', 'y', 'z'].map(c => ({ [c]: start[c] + (end[c] - start[c]) / 2 })));
            Object.assign(sprite.position, middlePos);
        }}

        nodeOpacity={0.3}
        nodeColor={node => !node.childLinks.length ? 'purple' : node.collapsed ? 'blue' : 'yellow'}
        nodeThreeObjectExtend={true}
        nodeThreeObject={async( node ) => {
            const imgTexture = new THREE.TextureLoader().load(await genTableImg(node));
            imgTexture.colorSpace = THREE.SRGBColorSpace;
            const material = new THREE.SpriteMaterial({ map: imgTexture });
            const sprite = new THREE.Sprite(material);
            sprite.scale.set(12, 12);
            return sprite;
        }}

        /* nodeThreeObject={(node) => {
            console.log(node);
            const nodeProperties = Object.keys(node).filter((key) => (key !== "childLinks" && key !== "collapsed" && key !== "index" && key !== "vx" && key !== "vz" && key !== "vy" && key !== "x" && key !== "y" && key !== "z" && key !== "id"));
            const table = document.createElement('table');
            table.border = true;
            const tableBorderColor = !node.childLinks.length ? 'purple' : node.collapsed ? 'blue' : 'yellow';
            table.style.border = `2px solid ${tableBorderColor}`;
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