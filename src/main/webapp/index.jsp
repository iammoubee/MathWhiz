<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${initParam.applicationTitle}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="page-shell welcome-shell">
    <header class="topbar">
        <a class="brand" href="${pageContext.request.contextPath}/">
            <span class="brand-mark">π</span><span>MathWhiz</span>
        </a>
        <span class="difficulty-pill"><span class="status-dot"></span> NORMAL MODE</span>
    </header>

    <main class="welcome-layout">
        <section class="welcome-copy">
            <p class="eyebrow">ICT · MBSTU &nbsp; / &nbsp; LIVE LAB TEST #2</p>
            <h1>Think clearly.<br><span>Calculate boldly.</span></h1>
            <p class="intro">Four quick challenges in number theory and trigonometry. Put your mathematical instincts to work and see where you land.</p>
            <div class="feature-row">
                <div class="feature-item"><strong>04</strong><span>questions</span></div>
                <div class="feature-divider"></div>
                <div class="feature-item"><strong>10</strong><span>max points</span></div>
                <div class="feature-divider"></div>
                <div class="feature-item"><strong>°</strong><span>degrees</span></div>
            </div>
            <div class="math-doodle doodle-one">∑</div>
            <div class="math-doodle doodle-two">√x</div>
            <div class="math-doodle doodle-three">sin θ</div>
        </section>

        <section class="panel start-panel">
            <div class="panel-icon">✳</div>
            <p class="eyebrow">READY WHEN YOU ARE</p>
            <h2>Start your round</h2>
            <p class="muted">Enter your name to begin. Your score can join the leaderboard.</p>
            <c:if test="${not empty nameError}">
                <div class="alert alert-error" role="alert"><c:out value="${nameError}"/></div>
            </c:if>
            <form action="${pageContext.request.contextPath}/game" method="post" class="stack-form">
                <input type="hidden" name="action" value="start">
                <label for="playerName">Player name</label>
                <input id="playerName" name="playerName" type="text" maxlength="100" autocomplete="name"
                       placeholder="e.g. Alex Morgan" value="<c:out value='${playerName}'/>" required>
                <button class="button button-primary button-full" type="submit">Start the challenge <span aria-hidden="true">→</span></button>
            </form>
            <div class="panel-separator"><span>OR</span></div>
            <a class="button button-secondary button-full" href="${pageContext.request.contextPath}/game?action=leaderboard">
                <span aria-hidden="true">↗</span> View leaderboard
            </a>
        </section>
    </main>

    <footer class="site-footer">
        <span><c:out value="${initParam.applicationTitle}"/></span>
        <span>JAVA WEB APPLICATION DEVELOPMENT &nbsp; · &nbsp; TOTAL MARKS 10</span>
    </footer>
</div>
</body>
</html>
