const api = {
    fetchStocks: async () => {
        const response = await fetch('/api/stocks');
        return response.json();
    },

    fetchPosts: async (stockCode) => {
        const response = await fetch(`/api/posts?stockCode=${encodeURIComponent(stockCode)}`);
        return response.json();
    },

    fetchPostDetail: async (postId) => {
        const response = await fetch(`/api/posts?postId=${encodeURIComponent(postId)}`);
        return response.json();
    },

    createPost: async ({ stockCode, title, content, writer, postPassword }) => {
        const payload = new URLSearchParams();
        payload.append('stockCode', stockCode);
        payload.append('title', title);
        payload.append('content', content);
        payload.append('writer', writer);
        payload.append('postPassword', postPassword);

        const response = await fetch('/api/posts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: payload.toString()
        });
        return response.text();
    },

    deletePost: async ({ postId, password }) => {
        const response = await fetch(`/api/posts?postId=${encodeURIComponent(postId)}&password=${encodeURIComponent(password)}`, {
            method: 'DELETE'
        });
        return response.text();
    },

    fetchComments: async (postId) => {
        const response = await fetch(`/api/comments?postId=${encodeURIComponent(postId)}`);
        return response.json();
    },

    createComment: async ({ postId, writer, content }) => {
        const payload = new URLSearchParams();
        payload.append('postId', postId);
        payload.append('writer', writer);
        payload.append('content', content);

        const response = await fetch('/api/comments', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: payload.toString()
        });
        return response.text();
    }
};
