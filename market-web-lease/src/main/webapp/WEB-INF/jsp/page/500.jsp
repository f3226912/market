<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <div class="error-head"> </div>

      <section class="error-wrapper text-center">
          <h1><img src="images/500.png" alt=""></h1>
          <div class="error-desk">
              <h2>错误提示</h2>
              <p class="nrml-txt"><font color="red">${requestScope.errorMsg }</font></p>
          </div>
      </section>

