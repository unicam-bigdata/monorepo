import { COLUMNDATATYPE } from "../data/column-datatype";
import { FILTEROPERATOR } from "../data/filter-operator";
import { axiosClient } from "./axiosClient";
import { endpoints } from "./endpoints";
export async function getRelatedNodes(label, key, value) {
    const config = {
        nodeLabel: label,
        nodeFilter: {
            propertyName: key,
            filterOperator: FILTEROPERATOR.EQUALS,
            value: value,
            dataType: COLUMNDATATYPE.STRING
        },
    };
    return axiosClient.post(endpoints.getRelatedNodes, config);
};