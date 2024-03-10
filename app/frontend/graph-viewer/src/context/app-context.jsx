import React, { createContext, useState } from "react";

export const AppContext = createContext("");

export const AppContextProvider = ({ children }) => {
    const [identifiers, setIdentifiers] = useState();
    const [data, setData] = useState();
    const [expandedNodes, setExpandedNodes] = useState([]);



    return (<AppContext.Provider value={{ identifiers, setIdentifiers, data, setData, expandedNodes, setExpandedNodes }}>
        {children}
    </AppContext.Provider>);
}