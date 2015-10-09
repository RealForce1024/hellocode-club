export function interceptor(resp, func) {
    if (resp.status >= 200 && resp.status < 300) {
        func()
    } else if (resp.status == 401) {
        window.location.href = '/auth/signin'
    } else {
        console.warn(resp.status + ': ' + resp.text)
    }
}