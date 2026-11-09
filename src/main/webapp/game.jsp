<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Your challenge · MathWhiz</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-shell">
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/"><span class="brand-mark">π</span><span>MathWhiz</span></a>
        <span class="difficulty-pill"><span class="status-dot"></span> NORMAL MODE</span>
    </header>

    <main class="content-width">
        <section class="page-heading">
            <div>
                <p class="eyebrow">YOUR CHALLENGE</p>
                <h1>Show what you know<span class="heading-period">.</span></h1>
                <p class="muted">Good luck, <strong><c:out value="${playerName}"/></strong>. You have four questions worth 10 points.</p>
            </div>
            <div class="angle-note"><span>∠</span> Trigonometry inputs are in degrees</div>
        </section>

        <c:if test="${not empty validationErrors}">
            <div class="alert alert-error" role="alert">Please correct the highlighted answers and submit again.</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/game" method="post" class="question-form">
            <input type="hidden" name="action" value="submit">
            <div class="question-grid">
                <c:forEach var="question" items="${gameQuestions}">
                    <article class="question-card">
                        <div class="question-topline">
                            <span class="question-number">0<c:out value="${question.number}"/></span>
                            <span class="marks-badge"><c:out value="${question.marks}"/> points</span>
                        </div>
                        <c:choose>
                            <c:when test="${question.typeName == 'PRIME'}">
                                <span class="topic-label">NUMBER THEORY</span>
                            </c:when>
                            <c:when test="${question.typeName == 'MODULUS'}">
                                <span class="topic-label">MODULUS</span>
                            </c:when>
                            <c:otherwise>
                                <span class="topic-label">TRIGONOMETRY</span>
                            </c:otherwise>
                        </c:choose>
                        <h2><c:out value="${question.prompt}"/></h2>
                        <label for="answer<c:out value='${question.number}'/>">Your answer</label>
                        <input id="answer<c:out value='${question.number}'/>"
                               name="answer<c:out value='${question.number}'/>"
                               type="text" inputmode="${question.typeName == 'TRIGONOMETRY' ? 'decimal' : 'numeric'}"
                               autocomplete="off" placeholder="${question.typeName == 'TRIGONOMETRY' ? 'e.g. 0.500' : 'Enter your answer'}"
                               aria-describedby="error<c:out value='${question.number}'/>" required>
                        <c:if test="${not empty validationErrors[question.number]}">
                            <p id="error<c:out value='${question.number}'/>" class="field-error" role="alert">
                                <c:out value="${validationErrors[question.number]}"/>
                            </p>
                        </c:if>
                    </article>
                </c:forEach>
            </div>
            <div class="submit-row">
                <p>Take your time — the score is calculated securely on the server.</p>
                <button class="button button-primary" type="submit">Submit answers <span aria-hidden="true">→</span></button>
            </div>
        </form>
    </main>
    <footer class="site-footer"><span>MathWhiz · NORMAL</span><span>MAXIMUM SCORE &nbsp; 10 / 10</span></footer>
</div>
</body>
</html>
