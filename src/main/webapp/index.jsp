<html lang="en">
<head>
    <title>PurchaseOrderAPI REST Example</title>
</head>
<body>
<p>
    Here are the REST services (all return json).
<ul>
    <li><em>Note 1: For all services context path is http://localhost:1234/PurchaseOrderApi</em></li>
</ul>

<table border="1">
    <tbody>
    <tr>
        <th><em>Entity</em></th>
        <th><b>PurchaseOrder</b></th>
    </tr>
    <tr>
        <td><em>Find</em></td>
        <td>GET /api/order/ORDER_ID</td>
    </tr>
    <tr>
        <td><em>List</em></td>
        <td>GET /api/order/list</td>
    </tr>
    <tr>
        <td><em>Create</em></td>
        <td>POST /api/order/create</td>
    </tr>
    <tr>
        <td><em>Update</em></td>
	<td>PUT /api/order/update/ORDER_ID?price=xx.x&quantity=x&discount=x%25&origin=xxx&description=xxxx</td>
    </tr>
    <tr>
        <td><em>Delete</em></td>
        <td>DELETE /api/order/ORDER_ID</td>
    </tr>
    </tbody>
</table>
</p>
</body>
</html>
