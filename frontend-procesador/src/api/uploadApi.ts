// Get API backend URL from environment variables with fallback
const API_BACKEND = process.env.REACT_APP_API_BACKEND;


export async function uploadFile(formData: FormData): Promise<Response> {
  return await fetch(`${API_BACKEND}/api/excel/upload`, {
    method: "POST",
    body: formData,
  });
}
