import { useState } from "react";
import { QueryModal } from "../components/query-modal";

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
    return (
        <>
            <button className="btn" id="collapseButton" onClick={() => collapsePanel()}>Panel</button>
            <div className="sidePanel" id="sidePanel">
                <button className="btn" disabled={openModal} onClick={() => setOpenModal(true)}>Query</button>
                {openModal && <QueryModal setOpenModal={setOpenModal} />}
                <button className="btn" onClick={() => collapsePanel()}>Panel</button>
            </div>
        </>
    )
}