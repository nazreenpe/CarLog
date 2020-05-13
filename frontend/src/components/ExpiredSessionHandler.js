const handleExpiredSession = (response) => {
    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("currentUser")
        window.location.replace("/#/login")
    }
    return response
}

export default handleExpiredSession