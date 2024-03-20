import { useEffect, useState, useContext } from "react";
import { AppContext } from "../context/app-context";
import { useForm, useFieldArray } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"
import { FilterOption } from "./filter-option";
import { queryFormSchema } from "../data/query-form-schema";
import { Input } from "./Input";
import { getNodeProperties } from "../api/get-node-properties";
import { getNodes } from "../api/get-nodes";


export function QueryModal({ setOpenModal }) {
    const { identifiers, setData } = useContext(AppContext);
    const [options, setOptions] = useState([]);
    const [keys, setKeys] = useState([]);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);

    const defaultValues = {
        label: "",
        filter: []
    };

    const {
        register,
        handleSubmit,
        formState: { errors, touchedFields },
        control,
        setValue,
        getValues
    } = useForm({
        defaultValues,
        resolver: yupResolver(queryFormSchema),
        shouldFocusError: false
    })

    const filterFieldArray = useFieldArray({
        control,
        name: "filter",
    });

    const reactHookForm = {
        filterFieldArray: filterFieldArray,
        control: control,
        register: register,
        errors: errors,
        touchedFields: touchedFields,
        isSubmitted: isSubmitClicked
    };

    const onSubmit = async (data) => {

        const query = { nodeName: data.label };

        if (data.filter.length > 0) {
            const filter = data?.filter?.map((item) => item.condition.map((conditionItem) => ({
                propertyName: conditionItem.key,
                filterOperator: conditionItem.filterOperator,
                value: conditionItem.value,
                dataType: conditionItem.datatype
            })));
            query.filter = filter;
        }

        try {

            const result = await getNodes(query);

            const nodeKey = identifiers?.find((item) => item.label === data.label).key;
            const nodes = result.data.map((item) => ({ label: data.label, ...item, id: item[nodeKey], collapsed: true }));
            const links = [];

            const relationStructure = {
                nodes,
                links
            };
            setData(relationStructure);
            setOpenModal(false);

        } catch (exception) {

            console.log(exception);
        }

    };

    const onLabelChange = async (e) => {
        if (e.target.value) {
            try {
                const result = await getNodeProperties(e.target.value);
                setKeys([{ label: "Choose key", value: "" }, ...result.data.map((item) => ({ label: item, value: item }))]);
            } catch (exception) {
                console.log(exception);
            }

        } else {
            setKeys([]);
        }

    }

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
                    <Input type={"select"} label={"Label"} error={touchedFields?.label || isSubmitClicked ? errors.label?.message : undefined} selectOptions={options} {...register("label", {
                        onChange: onLabelChange
                    })} />
                }
                <FilterOption keys={keys} reactHookForm={reactHookForm} />

                <div className="modal-buttons-container">
                    <button type="submit" className="btn" onClick={() => setIsSubmitClicked(true)}>Query</button>
                    <button className="btn-outline" onClick={() => setOpenModal(false)}>Cancel</button>
                </div>
            </form>

        </div>
    );
}