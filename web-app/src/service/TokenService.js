class TokenService{
    getLocalAccessToken(){
        const token = localStorage.getItem("token");
        return token;
    }

    setLocalAccessToken(token){
        localStorage.setItem("token", token);
    }

    removeLocalAccessToken(){
        localStorage.removeItem("token");
    }
}
export default new TokenService();