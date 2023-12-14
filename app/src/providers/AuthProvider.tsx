import { useNavigate } from "react-router-dom";
import { AppState, Auth0Provider } from "@auth0/auth0-react";

const onRedirectCallback = (appState?: AppState) => {
  const navigate = useNavigate();
  navigate(appState && appState.returnTo ? appState.returnTo : window.location.pathname);
};

const providerConfig = {
  domain: import.meta.env.AUTH_DOMAIN,
  clientId: import.meta.env.AUTH_CLIENT_ID,
  onRedirectCallback,
  authorizationParams: {
    redirect_uri: window.location.origin,
    audience: import.meta.env.AUTH_AUDIENCE,
  },
};

interface AuthProviderProps {
  children: React.ReactNode;
}

export default function AuthProvider({ children }: AuthProviderProps) {
  return <Auth0Provider {...providerConfig}>{children}</Auth0Provider>;
}
