import * as yup from "yup";

const requiredMessage = "This field is required.";

export const queryFormSchema = yup.object({
    label: yup.string().required(requiredMessage),
    filter: yup.array(yup.object().shape({
        condition: yup.array(yup.object().shape({
            filterOperator: yup.string().required(requiredMessage),
            datatype: yup.string().required(requiredMessage),
            key: yup.string().required(requiredMessage),
            value: yup.string().required(requiredMessage)
        }))
    }))
}).required();