import { toJpeg } from 'html-to-image';

export async function genTableImg(node) {
    const nodeProperties = Object.keys(node).filter((key) => (key !== "childLinks" && key !== "collapsed" && key !== "index" && key !== "vx" && key !== "vz" && key !== "vy" && key !== "x" && key !== "y" && key !== "z" && key !== "id"));
    const table = document.createElement('table');
    //table.border = true;
    const tableBorderColor = !node.childLinks.length ? 'purple' : node.collapsed ? 'blue' : 'yellow';
    table.style.border = `3px solid ${tableBorderColor}`;
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
    document.body.append(table);

    return await toJpeg(table).then(function (imgUrl) {
        return imgUrl;
    })
}