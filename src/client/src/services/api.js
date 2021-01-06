export const baseUrl = "http://192.168.0.111:5000/api/";

export const headers = (accessToken) => {
	return {
		headers: {
			Authorization: "Bearer " + accessToken,
		},
	};
};