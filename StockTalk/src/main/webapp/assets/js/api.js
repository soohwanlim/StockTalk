const api = {
    fetchStocks: async (search = '', page = 1) => {
        const query = new URLSearchParams({ search, page });
        const response = await fetch(`/api/stocks?${query.toString()}`);
        return handleApiResponse(response);
    },

    fetchPosts: async (stockCode, search = '') => {
        const query = new URLSearchParams({ stockCode, search });
        const response = await fetch(`/api/posts?${query.toString()}`);
        return handleApiResponse(response);
    },

    fetchPostDetail: async (postId) => {
        const response = await fetch(`/api/posts?postId=${encodeURIComponent(postId)}`);
        return handleApiResponse(response);
    },

    createPost: async ({ stockCode, title, content, postPassword }) => {
        const payload = new URLSearchParams();
        payload.append('stockCode', stockCode);
        payload.append('title', title);
        payload.append('content', content);
        payload.append('postPassword', postPassword);

        const response = await fetch('/api/posts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: payload.toString()
        });
        return handleApiResponse(response);
    },

    deletePost: async ({ postId }) => {
        const response = await fetch(`/api/posts?postId=${encodeURIComponent(postId)}`, {
            method: 'DELETE'
        });
        return handleApiResponse(response);
    },

    updatePost: async ({ postId, title, content }) => {
        const payload = new URLSearchParams();
        payload.append('postId', postId);
        payload.append('title', title);
        payload.append('content', content);

        const response = await fetch('/api/posts', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: payload.toString()
        });
        return handleApiResponse(response);
    },

    fetchComments: async (postId) => {
        const response = await fetch(`/api/comments?postId=${encodeURIComponent(postId)}`);
        return handleApiResponse(response);
    },

    createComment: async ({ postId, content }) => {
        const payload = new URLSearchParams();
        payload.append('postId', postId);
        payload.append('content', content);

        const response = await fetch('/api/comments', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: payload.toString()
        });
        return handleApiResponse(response);
    },

    deleteComment: async (commentId) => {
        const response = await fetch(`/api/comments?commentId=${encodeURIComponent(commentId)}`, {
            method: 'DELETE'
        });
        return handleApiResponse(response);
    }
};

async function handleApiResponse(response) {
    if (response.status === 401) {
        window.location.href = '/pages/login';
        return;
    }
    const text = await response.text();
    if (!response.ok) {
        try {
            const json = JSON.parse(text);
            throw new Error(json.error || json.message || 'API 요청 중 오류가 발생했습니다.');
        } catch (e) {
            throw new Error(text || 'API 요청 중 오류가 발생했습니다.');
        }
    }
    try {
        return JSON.parse(text);
    } catch (e) {
        return text;
    }
}
