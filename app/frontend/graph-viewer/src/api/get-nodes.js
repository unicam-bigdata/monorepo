import { axiosClient } from "./axiosClient";
import { endpoints } from "./endpoints";
export async function getNodes(config) {
    return axiosClient.post(endpoints.getNodes, { ...config });
};