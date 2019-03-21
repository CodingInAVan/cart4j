export interface LoginRequest {
  readonly username: string;
  readonly password: string;
}
export interface TokenReponse {
  readonly access_token: string;
  readonly token_type: string;
  readonly refresh_token: string;
  readonly expires_in: number;
  readonly scope: string;
}
export interface LoginStatus {
  readonly code: string,
  readonly message: string
}
export interface Client {
  id: number,
  clientUniqueId: string,
  grantTypes: string
}

export interface Page<T> {
  list: T[],
  offset: number,
  totalPage: number,
  limit: number,
  totalRecords: number
}
