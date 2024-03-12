import { ConditionContainer } from "./ConditionContainer";

export function FilterOption({ filterFieldArray, control, register, errors }) {
    return (
        <>
            {
                filterFieldArray.fields.map((item, index) => (
                    <div className="filter-input-container" key={item.id}>
                        <div className="filter-label">{`Filter ${index + 1}`}</div>
                        <div className="remove-filter-container">
                            <button className="btn-outline" onClick={() => { filterFieldArray.remove(filterFieldArray.fields.length - 1) }}>Remove filter</button>
                        </div>
                        <ConditionContainer index={index} control={control} register={register} errors={errors} />

                    </div>))
            }
        </>

    );
}