import React, { createContext, useState } from "react";

export const AppContext = createContext("");

export const AppContextProvider = ({ children }) => {
    const [identifiers, setIdentifiers] = useState();
    const [data, setData] = useState();
    const [expandedNodes, setExpandedNodes] = useState([]);
    const [linkTextVisible, setLinkTextVisible] = useState(true);
    const [linkTextHeight, setLinkTextHeight] = useState(4);

    function hideLinkText() {
        if (linkTextVisible) {
            setLinkTextVisible(false);
            setLinkTextHeight(0);
        } else {
            setLinkTextVisible(true);
            setLinkTextHeight(4);
        }
    }

    return (<AppContext.Provider value={{ identifiers, setIdentifiers, data, setData, expandedNodes, setExpandedNodes,linkTextVisible,linkTextHeight,hideLinkText }}>
        {children}
    </AppContext.Provider>);
}