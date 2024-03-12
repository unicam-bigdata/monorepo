import { useEffect, useState, useContext } from "react";
import { AppContext } from "../context/app-context";
import { useForm, useFieldArray } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"
import * as yup from "yup"
import { FilterOption } from "./filter-option";
import { queryFormSchema } from "../data/query-form-schema";
import { Input } from "./Input";


export function QueryModal({ setOpenModal }) {
    const { identifiers } = useContext(AppContext);
    const [options, setOptions] = useState([]);

    const defaultValues = {
        label: "",
        filter: [
            {
                condition: [{ filterOperator: "", datatype: "", key: "", value: "" }]
            },
        ]
    };

    const {
        register,
        handleSubmit,
        formState: { errors },
        control,
        setValue,
        getValues
    } = useForm({
        defaultValues,
        resolver: yupResolver(queryFormSchema)
    })

    const filterFieldArray = useFieldArray({
        control,
        name: "filter",
    });

    const onSubmit = (data) => console.log(data);

    useEffect(() => {
        if (identifiers) {
            setOptions([{ label: "Please select label", value: "" }, ...identifiers.map((item) => ({ label: item.label, value: item.label }))]);
        }
    }, [identifiers]);

    return (
        <div className="modal-container">

            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="modal-buttons-container">
                    <button className="btn" onClick={() => {
                        setValue("filter", [
                            ...(getValues().filter || []),
                            {
                                condition: [{ filterOperator: "", datatype: "", key: "", value: "" }]
                            }
                        ]);
                    }}>Add filter</button>
                </div>
                {options.length > 0 &&
                    <Input type={"select"} label={"Label"} error={errors.label?.message} selectOptions={options} {...register("label")} />
                }
                <FilterOption filterFieldArray={filterFieldArray} control={control} register={register} errors={errors} />

                <div className="modal-buttons-container">
                    <button type="submit" className="btn">Query</button>
                    <button className="btn-outline" onClick={() => setOpenModal(false)}>Cancel</button>
                </div>
            </form>

        </div>
    );
}