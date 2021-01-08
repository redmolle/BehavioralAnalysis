export const baseUrl = "/api/";

export const headers = (accessToken) => {
	return {
		headers: {
			Authorization: "Bearer " + accessToken,
		},
	};
};