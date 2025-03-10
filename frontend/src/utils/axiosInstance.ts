import axios from "axios";

const isDevelopment = process.env.NODE_ENV === "development";

const apiClient = isDevelopment
  ? axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_BASE_URL,
      headers: {
        "Content-Type": "application/json",
      },
      auth: {
        username: process.env.NEXT_PUBLIC_API_USERNAME || "",
        password: process.env.NEXT_PUBLIC_API_PASSWORD || "",
      },
    })
  : axios.create({
      headers: {
        "Content-Type": "application/json",
      },
      withCredentials: true,
    });

export default apiClient;
