**🛒 RevShop — Console-Based E-Commerce Application**

**Overview**

*RevShop is a menu-driven, console-based e-commerce application that simulates real-world online shopping workflows using a relational database backend.*
*The application supports buyer and seller roles, allowing users to browse products, manage carts, place orders, write reviews, and receive notifications — all through a command-line interface.*

**🎯 Project Objectives**

*Demonstrate strong ER modeling and relational database design*

*Implement realistic e-commerce workflows in a console environment*

*Enforce role-based access control*

*Maintain data integrity and normalization*

**👥 User Roles**

**👤 Buyer**

*Register and log in*

*Browse available products*

*Add products to cart*

*Update or remove cart items*

*Check out* 

*Place orders and receive payment notification*

*Add products to favorites*

*Write product reviews*

**🏪 Seller**

*Register and log in*

*Add new products*

*Update product price and stock*

*Manage inventory*

*Receive order and low-stock notifications*

**🧱 Application Workflow (Console)**

**1️⃣ User Authentication**

*Users log in via console prompts*

*Credentials are validated*

*Menu options are displayed based on user role*

**2️⃣ Product Management**

*Buyers can view and browse products*

*Sellers can add, update, or manage their products*

*Product data is stored in the PRODUCTS table*

**3️⃣ Cart Management**

*Each buyer has one active cart*

*Buyers can:*

*Add products to cart*

*Update quantities*

*Remove items*

Cart data is stored using:

CARTS

CART_ITEMS

**4️⃣ Order Processing**

*Buyer can see the products that they have added in the cart to the check out page with total price*

*Asks confirmation to proceed with order*

*Asks for payment method*

*Asks confirmation to proceed with payment*

*Order placed notification received*

Managed using

ORDER_ITEMS

**5️⃣ Favorites & Reviews**

*Buyers can favorite products*

*Buyers can leave reviews after purchase*

Managed using:

FAVORITES

PRODUCT_REVIEWS

*These features increase user engagement even in a console-based system.*

**6️⃣ Notifications**

*Order placed messages are stored in NOTIFICATIONS*

*Displayed to users(sellers) when they log in*

**Examples:**

*Order confirmation*

*Low stock will be visible whenever seller view products*

Review-related updates

**🗄️ Database Design**

*RevShop uses a fully normalized relational database with:*

*Primary and foreign keys*

*Bridge tables for many-to-many relationships*

**📌 The ERD is included in this repository:**

*docs/RevshopERD.png*




