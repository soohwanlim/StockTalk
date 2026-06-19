function makeDateStr(year, month, day, type) {
    return year + type + ((month < 10) ? '0' + month : month) + type + ((day < 10) ? '0' + day : day);
}

function makeTimeStr(hour, minute, second, type) {
    return hour + type + ((minute < 10) ? '0' + minute : minute) + type + ((second < 10) ? '0' + second : second);
}

function makePaginationHtml(listRowCount, pageLinkCount, currentPageIndex, totalListCount, htmlTargetId) {
    const targetUI = document.querySelector('#' + htmlTargetId);
    const pageCount = Math.ceil(totalListCount / listRowCount);

    let startPageIndex;
    if ((currentPageIndex % pageLinkCount) === 0) {
        startPageIndex = ((currentPageIndex / pageLinkCount) - 1) * pageLinkCount + 1;
    } else {
        startPageIndex = Math.floor(currentPageIndex / pageLinkCount) * pageLinkCount + 1;
    }

    let endPageIndex;
    if ((currentPageIndex % pageLinkCount) === 0) {
        endPageIndex = ((currentPageIndex / pageLinkCount) - 1) * pageLinkCount + pageLinkCount;
    } else {
        endPageIndex = Math.floor(currentPageIndex / pageLinkCount) * pageLinkCount + pageLinkCount;
    }

    let prev = currentPageIndex > pageLinkCount;
    let next = endPageIndex < pageCount;

    if (endPageIndex > pageCount) {
        endPageIndex = pageCount;
        next = false;
    }

    let paginationHTML = `<ul class="pagination justify-content-center">`;

    if (prev) {
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="javascript:movePage(${startPageIndex - 1});" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>`;
    }

    for (let i = startPageIndex; i <= endPageIndex; i++) {
        paginationHTML += `<li class="page-item ${i === currentPageIndex ? 'active' : ''}"><a class="page-link" href="javascript:movePage(${i});">${i}</a></li>`;
    }

    if (next) {
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="javascript:movePage(${endPageIndex + 1});" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>`;
    }

    paginationHTML += `</ul>`;
    targetUI.innerHTML = paginationHTML;
}
