function hasClass(el, className) {
    if (el.classList)
        return el.classList.contains(className)
    else
        return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}
function addClass(el, className) {
    if (el.classList)
        el.classList.add(className)
    else if (!hasClass(el, className))
        el.className += " " + className
}
function removeClass(el, className) {
    if (el.classList)
        el.classList.remove(className)
    else if (hasClass(el, className)) {
        var reg = new RegExp('(\\s|^)' + className + '(\\s|$)')
        el.className = el.className.replace(reg, ' ')
    }
}
function openModal(modalId, content) {
    var el = document.getElementById(modalId);
    debugger;
    var tableExist = false;
    if (hasClass(el, 'show')) {
        removeClass(el, 'show');
    } else {
        document.getElementById("annouceContent").innerHTML = content;
        addClass(el, 'show');
    }
}



