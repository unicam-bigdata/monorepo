import { useState } from "react";
import { QueryModal } from "../components/query-modal";

export const QueryContainer = () => {
    const [openModal, setOpenModal] = useState(false);
    return (
        <div className="query-container">
            <button className="btn" disabled={openModal} onClick={() => setOpenModal(true)}>Query</button>
            {openModal &&
                <QueryModal setOpenModal={setOpenModal} />
            }
        </div>
    )
}