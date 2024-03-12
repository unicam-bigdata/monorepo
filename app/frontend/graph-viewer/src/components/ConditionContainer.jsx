import { FILTEROPERATOR } from "../data/filter-operator";
import { COLUMNDATATYPE } from "../data/column-datatype";
import { Input } from "./Input";
import { useFieldArray } from "react-hook-form";

export function ConditionContainer({ index, control, register, errors }) {

    const filterOperatorOptions = [{ label: "Select filter operator", value: "" }, ...Object.keys(FILTEROPERATOR).map((item) => ({ label: item, value: item }))];
    const datatypeOptions = [{ label: "Select select data type", value: "" }, ...Object.keys(COLUMNDATATYPE).map((item) => ({ label: item, value: item }))];

    const conditionFieldArray = useFieldArray({
        control,
        name: `filter.${index}.condition`
    });
    return (
        <>
            {conditionFieldArray.fields.map((item, conditionIndex) => (
                <div className="condition-container" key={item.id}>
                    <div className="condition-label">Condition {conditionIndex + 1}</div>

                    <div className="condition-input-container">
                        <Input type={"select"} selectOptions={filterOperatorOptions} error={errors?.filter?.[index]?.condition?.[conditionIndex]?.filterOperator?.message} {...register(`filter.${index}.condition.${conditionIndex}.filterOperator`)} />
                        <Input type={"select"} selectOptions={datatypeOptions} error={errors?.filter?.[index]?.condition?.[conditionIndex]?.datatype?.message} {...register(`filter.${index}.condition.${conditionIndex}.datatype`)} />
                    </div>

                    <div className="condition-input-container">
                        <Input placeholder={"Key"} error={errors?.filter?.[index]?.condition?.[conditionIndex]?.key?.message} {...register(`filter.${index}.condition.${conditionIndex}.key`)} />
                        <Input placeholder={"Value"} error={errors?.filter?.[index]?.condition?.[conditionIndex]?.value?.message} {...register(`filter.${index}.condition.${conditionIndex}.value`)} />
                    </div>
                </div>
            ))}
            <div className="modal-buttons-container">
                <button className="btn-outline" onClick={() => conditionFieldArray.append({ filterOperator: "", datatype: "", key: "", value: "" })}>Add condition</button>
                <button className="btn-outline" disabled={conditionFieldArray.fields.length == 1} onClick={() => { if (conditionFieldArray.fields.length > 1) { conditionFieldArray.remove(conditionFieldArray.fields.length - 1) } }}>Remove condition</button>
            </div>
        </>

    );
}