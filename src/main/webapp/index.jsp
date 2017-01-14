<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p>API calls</p>

<p>There is one REST endpoint that represents making risk assessment of a purchase:</p>

<p>URL: /decision </p>

<p>Request (POST)</p>

<p>JSON Entity with the following keys: </p>

<pre><code>Parameter   Type    Description 
email       string  Email of the buyer 
first_name  string  First Name of the buyer 
last_name   string  Last Name of the buyer 
amount      integer The total amount of this 
                    purchase, in some fictitious currency
</code></pre>

<p>Response</p>

<p>HTTP 200 <br />
JSON entity with the following keys </p>

<pre><code>Key         Type    Description 
accepted    boolean Whether or not this purchase 
            should be accepted 
reason      string  The reason for not being 
                    accepted, or "ok" if accepted
</code></pre>

<p>Semantics</p>

<ul>
<li>If the amount is less than 10, it should always be accepted </li>
<li>If the amount is higher than 1000, it should always be rejected with the reason amount </li>
<li>If the sum of purchases from a particular email is larger than 1000 (including current 
purchase), it should be rejected with reason debt </li>
</ul> 
</body>
</html>
