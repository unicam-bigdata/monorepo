import { useState } from "react";
import { QueryModal } from "../components/query-modal";

const collapsed = false;

function collapsePanel(){
    var x = document.getElementById("sidePanel");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

export const SidePanel = () => {
    const [openModal, setOpenModal] = useState(true);
    return (
        <div className="sidePanel" id="sidePanel">
            <button className="btn" disabled={openModal} onClick={() => setOpenModal(true)}>Query</button>
            {openModal && <QueryModal setOpenModal={setOpenModal} />}
            <button className="btn" onClick={() => collapsePanel()}>Panel</button>
        </div>
    )
}