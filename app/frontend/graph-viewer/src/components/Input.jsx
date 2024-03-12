import React, { useState } from "react";

export const Input = React.forwardRef(({ label, name, type, selectOptions, placeholder, error, ...reacthookForm }, ref) => {
    const [inputType, setInputType] = useState(type ? type : "text");
    const [options, setOptions] = useState(selectOptions ? selectOptions : []);

    return (
        <div>
            <div className="input-container">
                {label &&
                    <span className="input-label">{label}</span>
                }

                {inputType === "select" &&
                    <select name={name} ref={ref} className={`input-field ${error && "input-field-error"}`}  {...reacthookForm}>
                        {options.map((item) => (<option key={item.label} value={item.value}>{item.label}</option>))}
                    </select>
                }

                {inputType === "text" &&
                    <input type="text" name={name} ref={ref} className={`input-field ${error && "input-field-error"}`} placeholder={placeholder}  {...reacthookForm} />
                }
            </div>
            {error &&
                <p className="input-error-message">{error}</p>
            }

        </div>
    );

});
