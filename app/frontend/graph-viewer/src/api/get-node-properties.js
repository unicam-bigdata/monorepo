import { axiosClient } from "./axiosClient";
import { endpoints } from "./endpoints";
export async function getNodeProperties(label) {
    return axiosClient.get(endpoints.getNodePropterties+"/"+label);
};