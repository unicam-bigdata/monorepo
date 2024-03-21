import { useState, useContext } from "react";
import { QueryModal } from "../components/query-modal";
import { AppContext } from "../context/app-context";


const collapsed = false;

function collapsePanel(){
    var panel = document.getElementById("sidePanel");
    var collapseBtn = document.getElementById("collapseButton");

    if (panel.style.display === "none") {
        panel.style.display = "block";
        collapseBtn.style.display = "none";
    } else {
        panel.style.display = "none";
        collapseBtn.style.display = "block";
    }
}

export const SidePanel = () => {
    const [openModal, setOpenModal] = useState(true);
    const { hideLinkText } = useContext(AppContext);
    return (
        <>
            <button className="btn" id="collapseButton" onClick={() => collapsePanel()}>Panel</button>
            <div className="sidePanel" id="sidePanel">
                <button className="btn" disabled={openModal} onClick={() => setOpenModal(true)}>Query</button>
                {openModal && <QueryModal setOpenModal={setOpenModal} />}
                <button className="btn" onClick={() => collapsePanel()}>Panel</button>
                <button className="btn" id="hideLinkTextBtn" onClick={() => hideLinkText()}>Hide link text</button>
            </div>
        </>
    )
}