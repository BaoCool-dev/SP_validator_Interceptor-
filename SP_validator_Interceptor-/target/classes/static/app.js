// URL của GraphQL API backend, hãy chắc chắn port là 8066 giống trong application.properties
const API_URL = 'http://localhost:8066/graphql';

/**
 * Hàm chung để gửi yêu cầu đến API GraphQL.
 * @param {string} query - Chuỗi truy vấn GraphQL.
 * @param {object} variables - Các biến cho truy vấn (nếu có).
 * @returns {Promise<object>} Dữ liệu trả về từ API.
 */
async function fetchGraphQL(query, variables = {}) {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ query, variables }),
    });
    const result = await response.json();
    if (result.errors) {
        console.error('GraphQL Errors:', result.errors);
        alert('Có lỗi xảy ra, vui lòng xem console để biết chi tiết.');
    }
    return result.data;
}

// --- XỬ LÝ SẢN PHẨM ---
async function loadProducts(categoryId = null) {
    let query, dataKey, vars = {};
    if (categoryId && categoryId!== 'all') {
        // Yêu cầu 2: Lấy tất cả product của 01 category
        query = `
            query ProductsByCategory($catId: ID!) {
                productsByCategoryId(categoryId: $catId) { id title price user { fullname } }
            }`;
        vars = { catId: categoryId };
        dataKey = 'productsByCategoryId';
    } else {
        // Yêu cầu 1: Hiển thị tất cả product có price từ thấp đến cao
        query = `query { productsSortedByPriceAsc { id title price user { fullname } } }`;
        dataKey = 'productsSortedByPriceAsc';
    }
    const data = await fetchGraphQL(query, vars);
    renderProducts(data[dataKey]);
}

function renderProducts(products) {
    const list = document.getElementById('product-list');
    list.innerHTML = products.map(p => `
        <div class="item">
            <strong>${p.title}</strong> - $${p.price.toFixed(2)}<br>
            <small>Người bán: ${p.user.fullname}</small>
        </div>
    `).join('');
}

// --- XỬ LÝ CATEGORY (CHO BỘ LỌC) ---
async function loadCategories() {
    const query = `query { categories { id name } }`;
    const data = await fetchGraphQL(query);
    const select = document.getElementById('category-filter');
    data.categories.forEach(cat => {
        const option = document.createElement('option');
        option.value = cat.id;
        option.textContent = cat.name;
        select.appendChild(option);
    });
}

function handleFilterChange() {
    const categoryId = document.getElementById('category-filter').value;
    loadProducts(categoryId);
}

// --- XỬ LÝ USER (CRUD - YÊU CẦU 3) ---
async function loadUsers() {
    const query = `query { users { id fullname email phone } }`;
    const data = await fetchGraphQL(query);
    renderUsers(data.users);
}

function renderUsers(users) {
    const list = document.getElementById('user-list');
    list.innerHTML = users.map(u => `
        <div class="item">
            <span>${u.fullname} (${u.email})</span>
            <div>
                <button onclick="editUser('${u.id}', '${u.fullname}', '${u.email}', '${u.phone |

| ''}')">Sửa</button>
                <button onclick="deleteUser('${u.id}')">Xóa</button>
            </div>
        </div>
    `).join('');
}

document.getElementById('user-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('user-id').value;
    const userInput = {
        fullname: document.getElementById('fullname').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        phone: document.getElementById('phone').value,
    };

    const mutation = id
      ? `mutation UpdateUser($id: ID!, $user: UserInput!) { updateUser(id: $id, user: $user) { id } }`
        : `mutation CreateUser($user: UserInput!) { createUser(user: $user) { id } }`;
    
    const variables = id? { id, user: userInput } : { user: userInput };

    await fetchGraphQL(mutation, variables);
    resetForm();
    loadUsers();
});

function editUser(id, fullname, email, phone) {
    document.getElementById('user-id').value = id;
    document.getElementById('fullname').value = fullname;
    document.getElementById('email').value = email;
    document.getElementById('password').placeholder = "Nhập mật khẩu mới để thay đổi";
    document.getElementById('password').required = false;
    document.getElementById('phone').value = phone;
}

async function deleteUser(id) {
    if (confirm('Bạn có chắc muốn xóa người dùng này?')) {
        const mutation = `mutation DeleteUser($id: ID!) { deleteUser(id: $id) }`;
        await fetchGraphQL(mutation, { id });
        loadUsers();
    }
}

function resetForm() {
    document.getElementById('user-form').reset();
    document.getElementById('user-id').value = '';
    document.getElementById('password').placeholder = "Mật khẩu";
    document.getElementById('password').required = true;
}

// --- KHỞI CHẠY ỨNG DỤNG KHI TRANG ĐƯỢC TẢI ---
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    loadCategories();
    loadUsers();
});