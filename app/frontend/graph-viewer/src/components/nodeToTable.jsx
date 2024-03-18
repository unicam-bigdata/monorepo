export const nodeToTable = (node) => {
    
    const nodeProperties = Object.keys(node).filter((key) => (key !== "childLinks" && key !== "index" && key !== "collapsed" && key !== "vx" && key !== "vz" && key !== "vy" && key !== "x" && key !== "y" && key !== "z" && key !== "id"));
    const table = document.createElement('table');
    table.className = "nodeTable";

    // Create table header
    var headerRow = table.insertRow();

    var headerCell1 = headerRow.insertCell(0);
    var headerCell2 = headerRow.insertCell(1);
    headerCell1.className = "node-header";
    headerCell2.className = "node-header";
    headerCell1.innerHTML = "Property";
    headerCell2.innerHTML = "Value";

    // Create rows
    for (var i = 0; i < nodeProperties.length-1; i++) {
        var row = table.insertRow();
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        cell1.className = "node-body";
        cell2.className = "node-body";
        cell1.innerHTML = nodeProperties[i];
        cell2.innerHTML = node[nodeProperties[i]];
    }

    table.className = 'nodeTable';
    return table;
}