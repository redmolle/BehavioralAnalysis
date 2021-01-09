export const baseUrl = "https://localhost:3000/api/";

export const headers = (accessToken) => {
	return {
		headers: {
			Authorization: "Bearer " + accessToken,
		},
	};
};