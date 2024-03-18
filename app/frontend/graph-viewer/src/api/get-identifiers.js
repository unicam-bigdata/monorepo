import { axiosClient } from "./axiosClient";
import { endpoints } from "./endpoints";
export async function getIdentifiers() {
    return axiosClient.get(endpoints.getIdentifiers);
};