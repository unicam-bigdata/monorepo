import { FILTEROPERATOR } from "../data/filter-operator";
import { COLUMNDATATYPE } from "../data/column-datatype";
import { Input } from "./Input";
import { useFieldArray } from "react-hook-form";

export function ConditionContainer({ index, keys, reactHookForm }) {

    const filterOperatorOptions = [{ label: "Select filter operator", value: "" }, ...Object.keys(FILTEROPERATOR).map((item) => ({ label: item, value: item }))];
    const datatypeOptions = [{ label: "Select select data type", value: "" }, ...Object.keys(COLUMNDATATYPE).map((item) => ({ label: item, value: item }))];

    const conditionFieldArray = useFieldArray({
        control: reactHookForm.control,
        name: `filter.${index}.condition`
    });
    
    return (
        <>
            {conditionFieldArray.fields.map((item, conditionIndex) => {
                const touched = {};
                touched.filterOperator = reactHookForm.touchedFields?.filter?.[index]?.condition?.[conditionIndex]?.filterOperator;
                touched.datatype = reactHookForm.touchedFields?.filter?.[index]?.condition?.[conditionIndex]?.datatype;
                touched.key = reactHookForm.touchedFields?.filter?.[index]?.condition?.[conditionIndex]?.datatype;
                touched.value = reactHookForm.touchedFields?.filter?.[index]?.condition?.[conditionIndex]?.value;

                const error = {};
                error.filterOperator = reactHookForm.errors?.filter?.[index]?.condition?.[conditionIndex]?.filterOperator?.message;
                error.datatype = reactHookForm.errors?.filter?.[index]?.condition?.[conditionIndex]?.datatype?.message;
                error.key = reactHookForm.errors?.filter?.[index]?.condition?.[conditionIndex]?.key?.message;
                error.value = reactHookForm.errors?.filter?.[index]?.condition?.[conditionIndex]?.value?.message;

                return (
                    <div className="condition-container" key={item.id}>
                        <div className="condition-label">Condition {conditionIndex + 1}</div>

                        <div className="condition-input-container">
                            <Input type={"select"} selectOptions={filterOperatorOptions} error={touched.filterOperator || reactHookForm.isSubmitted ? error.filterOperator : undefined} {...reactHookForm.register(`filter.${index}.condition.${conditionIndex}.filterOperator`)} />
                            <Input type={"select"} selectOptions={datatypeOptions} error={touched.datatype || reactHookForm.isSubmitted ? error.datatype : undefined} {...reactHookForm.register(`filter.${index}.condition.${conditionIndex}.datatype`)} />
                        </div>

                        <div className="condition-input-container"> 
                            {keys.length > 0 &&
                                <Input type={"select"} selectOptions={keys} placeholder={"Key"} error={touched.key || reactHookForm.isSubmitted ? error.key : undefined} {...reactHookForm.register(`filter.${index}.condition.${conditionIndex}.key`)} />
                            }
                            <Input placeholder={"Value"} error={touched.value || reactHookForm.isSubmitted ? error.value : undefined} {...reactHookForm.register(`filter.${index}.condition.${conditionIndex}.value`)} />
                        </div>
                    </div>
                )
            })}
            <div className="modal-buttons-container">
                <button className="btn-outline" onClick={() => conditionFieldArray.append({ filterOperator: "", datatype: "", key: "", value: "" })}>Add condition</button>
                <button className="btn-outline" disabled={conditionFieldArray.fields.length == 1} onClick={() => { if (conditionFieldArray.fields.length > 1) { conditionFieldArray.remove(conditionFieldArray.fields.length - 1) } }}>Remove condition</button>
            </div>
        </>

    );
}