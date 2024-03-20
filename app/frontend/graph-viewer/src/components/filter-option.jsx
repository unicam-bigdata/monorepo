import { ConditionContainer } from "./ConditionContainer";

export function FilterOption({ reactHookForm, keys }) {
    return (
        <>
            {
                reactHookForm.filterFieldArray.fields.map((item, index) => (
                    <div className="filter-input-container" key={item.id}>
                        <div className="filter-label">{`Filter ${index + 1}`}</div>
                        <div className="remove-filter-container">
                            <button className="btn-outline" onClick={() => { reactHookForm.filterFieldArray.remove(reactHookForm.filterFieldArray.fields.length - 1) }}>Remove filter</button>
                        </div>
                        <ConditionContainer index={index} keys={keys} reactHookForm={reactHookForm} />

                    </div>))
            }
        </>

    );
}