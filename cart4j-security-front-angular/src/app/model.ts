export interface LoginRequest {
  readonly username: string;
  readonly password: string;
}
export interface TokenReponse {
  readonly accessToken: string;
  readonly tokenType: string;
  readonly refreshToken: string;
  readonly expiresIn: Date;
  readonly scope: string;
}
export interface LoginStatus {
  readonly code: string,
  readonly message: string
}
