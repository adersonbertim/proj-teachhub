export function tokenValido(): boolean {
  const token = localStorage.getItem('teachhub_token');
  if (!token) return false;

  try {
    const payload = JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
    if (payload.exp && payload.exp * 1000 > Date.now()) {
      return true;
    }
  } catch {

  }

  localStorage.removeItem('teachhub_token');
  localStorage.removeItem('teachhub_role');
  return false;
}