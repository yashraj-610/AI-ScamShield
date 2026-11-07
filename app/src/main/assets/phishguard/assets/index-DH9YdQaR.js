(function(){let e=document.createElement(`link`).relList;if(e&&e.supports&&e.supports(`modulepreload`))return;for(let e of document.querySelectorAll(`link[rel="modulepreload"]`))n(e);new MutationObserver(e=>{for(let t of e)if(t.type===`childList`)for(let e of t.addedNodes)e.tagName===`LINK`&&e.rel===`modulepreload`&&n(e)}).observe(document,{childList:!0,subtree:!0});function t(e){let t={};return e.integrity&&(t.integrity=e.integrity),e.referrerPolicy&&(t.referrerPolicy=e.referrerPolicy),t.credentials=e.crossOrigin===`use-credentials`?`include`:e.crossOrigin===`anonymous`?`omit`:`same-origin`,t}function n(e){if(e.ep)return;e.ep=!0;let n=t(e);fetch(e.href,n)}})();var e={featureNames:[`url_length`,`hostname_length`,`path_length`,`num_dots`,`num_hyphens`,`num_subdomains`,`has_ip_host`,`has_at_symbol`,`num_query_params`,`has_punycode`,`has_homograph`,`is_https`,`num_digits_in_host`,`digit_ratio_host`,`suspicious_tld`,`is_shortener`,`num_suspicious_keywords`,`has_double_slash_in_path`,`num_special_chars`,`tld_length`],coef:[3.152392121841019,.01225668843055501,-1.240149149530153,.8396871560776821,.7031894573348321,-.33638522597427584,-.038618338899019305,.6720376277105335,-.7160182067646728,.21639504791107644,0,-1.2428188709045236,5.004364014558771,-1.158499085258049,.8857779796608821,.5581354456773491,.26809561250213315,.3820191474484523,.03861162409979746,-.3388567334010248],intercept:.8050840930511342,mean:[36.636175710594316,20.636375760606818,6.055497207635242,2.1468533800116694,.24252729849128948,.9876302408935568,.00208385429690756,.004801200300075019,.08910560973576727,.0003834291906309911,0,.7362674001833792,.7119779944986246,.025494704998437347,.03359173126614987,.002350587646911728,.0367425189630741,.0014837042593981828,.21688755522213887,3.010686004834542],scale:[41.39430239030117,8.716788747565372,32.376944535648505,1.0292331966900348,.689309317016984,.5959606974741654,.04560166497156029,.06912415479231161,.8626885686565189,.019577593638810575,1,.44065600598510074,2.491083854778533,.08308829772924325,.1801758220646575,.048425844180811965,.2190030031352317,.0384902959337422,3.085528318300292,.9094554864504711],threshold:.5,metrics:{accuracy:.8495699139827966,f1:.7931034482758621,precision:.7497832495231489,recall:.8417364220362079,roc_auc:.9073511248189072}},t=new Set(`co.uk,org.uk,me.uk,ltd.uk,plc.uk,net.uk,sch.uk,ac.uk,gov.uk,com.au,net.au,org.au,edu.au,gov.au,id.au,com.br,net.br,org.br,gov.br,co.in,net.in,org.in,gen.in,firm.in,ac.in,edu.in,gov.in,co.nz,net.nz,org.nz,govt.nz,ac.nz,co.za,org.za,net.za,co.jp,or.jp,ne.jp,ac.jp,go.jp,com.cn,net.cn,org.cn,gov.cn,com.mx,com.tr,com.sg,com.hk,com.tw,co.kr,co.id,co.th,com.my,com.ph,com.ar,com.co,com.ua,com.pl,com.ru`.split(`,`)),n=[`/`,`?`,`#`];function r(e){return e>=`a`&&e<=`z`||e>=`A`&&e<=`Z`}function i(e){return e>=`0`&&e<=`9`}function a(e){let t=e.indexOf(`://`);if(t<=0)return!1;let n=e.slice(0,t);if(!r(n[0]))return!1;for(let e of n)if(!(r(e)||i(e)||e===`+`||e===`-`||e===`.`))return!1;return!0}function o(e){if(e.startsWith(`[`)){let t=e.indexOf(`]`);if(t!==-1){let n=e.slice(0,t+1),r=e.slice(t+1);return r.startsWith(`:`)?[n,r.slice(1)]:[n,``]}}let t=e.lastIndexOf(`:`);if(t===-1)return[e,``];let n=e.slice(t+1);return n!==``&&[...n].every(i)?[e.slice(0,t),n]:[e,``]}function s(e){let t=e.trim(),r=a(t),i=r?t:`http://`+t,s=i.indexOf(`://`),c=i.slice(0,s).toLowerCase(),l=i.slice(s+3),u=l.length;for(let e of n){let t=l.indexOf(e);t!==-1&&t<u&&(u=t)}let d=l.slice(0,u),f=l.slice(u),p=``,m=d,h=d.lastIndexOf(`@`);h!==-1&&(p=d.slice(0,h),m=d.slice(h+1));let[g,_]=o(m);g=g.toLowerCase(),g.endsWith(`.`)&&!g.endsWith(`]`)&&(g=g.slice(0,-1));let v=``,y=``,b=f,x=b.indexOf(`#`);x!==-1&&(y=b.slice(x+1),b=b.slice(0,x));let S=b.indexOf(`?`);return S!==-1&&(v=b.slice(S+1),b=b.slice(0,S)),{original:t,href:i,scheme:c,userinfo:p,host:g,port:_,path:b,query:v,fragment:y,hadScheme:r}}var c=[`url_length`,`hostname_length`,`path_length`,`num_dots`,`num_hyphens`,`num_subdomains`,`has_ip_host`,`has_at_symbol`,`num_query_params`,`has_punycode`,`has_homograph`,`is_https`,`num_digits_in_host`,`digit_ratio_host`,`suspicious_tld`,`is_shortener`,`num_suspicious_keywords`,`has_double_slash_in_path`,`num_special_chars`,`tld_length`],l=new Set(`zip.mov.xyz.top.tk.gq.ml.cf.ga.country.kim.work.click.link.gdn.loan.download.review.stream.racing.win.bid.party.date.men.cricket.science.accountant.trade`.split(`.`)),u=new Set([`bit.ly`,`tinyurl.com`,`goo.gl`,`t.co`,`ow.ly`,`is.gd`,`buff.ly`,`cutt.ly`,`rebrand.ly`,`bit.do`,`shorturl.at`,`rb.gy`,`tiny.cc`,`t.ly`,`soo.gd`,`s.id`,`lnkd.in`,`db.tt`,`qr.ae`,`adf.ly`,`tr.im`,`v.gd`]),d=[`login`,`secure`,`account`,`verify`,`update`,`bank`,`confirm`,`signin`,`password`,`webscr`,`ebayisapi`];function f(e,t){let n=0;for(let r of e)r===t&&(n+=1);return n}function p(e){let t=0;for(let n of e)t+=1;return t}function m(e){let t=e.split(`.`);if(t.length!==4)return!1;for(let e of t)if(e===``||e.length>3||![...e].every(e=>e>=`0`&&e<=`9`)||Number.parseInt(e,10)>255)return!1;return!0}function h(e){if(!(e.startsWith(`[`)&&e.endsWith(`]`)))return!1;let t=e.slice(1,-1);return t===``||t.includes(`::`)&&(t.match(/::/g)??[]).length>1?!1:[...t].every(e=>`0123456789abcdefABCDEF:.`.includes(e))&&t.includes(`:`)}function g(e){return m(e)||h(e)}function _(e){for(let t of e)if((t.codePointAt(0)??0)>=128)return!1;return!0}function v(e){if(e===``||g(e))return[``,e,``];let n=e.split(`.`);if(n.length<2)return[``,e,``];let r=``;if(n.length>=3){let e=n.slice(-2).join(`.`);t.has(e)&&(r=e)}r===``&&(r=n[n.length-1]);let i=f(r,`.`)+1;if(n.length<=i)return[``,e,r];let a=n.slice(-(i+1)).join(`.`);return[n.slice(0,n.length-(i+1)).join(`.`),a,r]}function y(e){if(e===``)return 0;let t=0;for(let n of e.replace(/;/g,`&`).split(`&`))n!==``&&(t+=1);return t}function b(e){let t=e.original,n=t.toLowerCase(),r=e.host,i=r?r.split(`.`):[],[a,,o]=v(r),s=a===``?0:a.split(`.`).length,c=0;for(let e of r)e>=`0`&&e<=`9`&&(c+=1);let m=p(r),h=m>0?c/m:0,b=+!!i.some(e=>e.startsWith(`xn--`)),x=+!_(r),S=0;for(let e of d)n.includes(e)&&(S+=1);let C=0;for(let e of t)`%=?&_`.includes(e)&&(C+=1);return{url_length:p(t),hostname_length:m,path_length:p(e.path),num_dots:f(t,`.`),num_hyphens:f(r,`-`),num_subdomains:s,has_ip_host:+!!g(r),has_at_symbol:+!!t.includes(`@`),num_query_params:y(e.query),has_punycode:b,has_homograph:x,is_https:+(e.scheme===`https`),num_digits_in_host:c,digit_ratio_host:h,suspicious_tld:+!!l.has(o),is_shortener:+!!u.has(r),num_suspicious_keywords:S,has_double_slash_in_path:+!!e.path.includes(`//`),num_special_chars:C,tld_length:p(o)}}var x=[e=>e.has_ip_host>=1?{ruleId:`ip_host`,points:30,reason:`Uses a raw IP address instead of a domain name — legitimate sites almost always use a named domain.`,severity:`high`}:null,e=>e.has_at_symbol>=1?{ruleId:`at_symbol`,points:25,reason:`Contains an '@' symbol — a browser ignores everything before it, so the real destination can be hidden.`,severity:`high`}:null,e=>e.has_punycode>=1?{ruleId:`punycode`,points:22,reason:`Host uses Punycode ('xn--') — often used to spoof a trusted brand with look-alike characters.`,severity:`high`}:null,e=>e.has_homograph>=1&&e.has_punycode<1?{ruleId:`homograph`,points:20,reason:`Host contains non-ASCII characters that can visually imitate a trusted domain (homograph attack).`,severity:`high`}:null,e=>e.suspicious_tld>=1?{ruleId:`suspicious_tld`,points:18,reason:`Uses a top-level domain frequently associated with abuse (e.g. .zip, .xyz, .top, .tk).`,severity:`medium`}:null,e=>e.is_shortener>=1?{ruleId:`shortener`,points:15,reason:`This is a URL-shortening service — the real destination is hidden and cannot be inspected before clicking.`,severity:`medium`}:null,e=>e.num_subdomains>=3?{ruleId:`many_subdomains`,points:14,reason:`Has an unusually deep chain of subdomains — phishing pages bury a trusted brand name inside a long host to look legitimate.`,severity:`medium`}:null,e=>{let t=Math.trunc(e.num_suspicious_keywords);return t>=2?{ruleId:`suspicious_keywords`,points:16,reason:`Contains ${t} security/banking trigger words (login, verify, account, bank, …) — common bait in phishing links.`,severity:`medium`}:t===1?{ruleId:`suspicious_keyword_single`,points:7,reason:`Contains a security/banking trigger word (login, verify, account, …) — mildly suspicious on its own.`,severity:`low`}:null},e=>e.num_hyphens>=3?{ruleId:`many_hyphens`,points:12,reason:`Hostname is stuffed with hyphens — a pattern used to assemble fake brand names like 'secure-login-yourbank-com'.`,severity:`medium`}:null,e=>e.has_ip_host<1&&e.digit_ratio_host>=.3?{ruleId:`digit_heavy_host`,points:12,reason:`The hostname is unusually full of digits — legitimate brand domains are rarely mostly numbers.`,severity:`medium`}:null,e=>e.has_double_slash_in_path>=1?{ruleId:`double_slash_path`,points:10,reason:`Path contains '//' — sometimes used to smuggle a second URL or trigger an open redirect.`,severity:`low`}:null,e=>e.url_length>=100?{ruleId:`long_url`,points:10,reason:`The URL is very long — excessive length is often used to push the deceptive part out of view in the address bar.`,severity:`low`}:null,e=>e.is_https<1?{ruleId:`no_https`,points:10,reason:`Does not use HTTPS — traffic is not encrypted and the site's identity is not verified.`,severity:`low`}:null];function S(e){let t=[],n=0;for(let r of x){let i=r(e);i!==null&&(t.push(i),n+=i.points)}return{score:Math.max(0,Math.min(100,n)),hits:t}}var C=.6,w=.4;function T(e){if(e>=0)return 1/(1+Math.exp(-e));let t=Math.exp(e);return t/(1+t)}function E(t){return t.map((t,n)=>{let r=e.scale[n]===0?1:e.scale[n];return(t-e.mean[n])/r})}function D(t){let n=E(t),r=e.intercept;for(let t=0;t<n.length;t+=1)r+=e.coef[t]*n[t];return r}function O(e){return T(D(e))}function k(e){return e>=65?`Dangerous`:e>=35?`Suspicious`:`Safe`}function A(e){return Math.round(e*10)/10}function j(t){let n=s(t),r=b(n),i=c.map(e=>r[e]),a=S(r),o=O(i),l=o*100*C+w*a.score;l=Math.max(0,Math.min(100,l));let u=E(i),d=c.map((t,n)=>[t,e.coef[n]*u[n]]);d.sort((e,t)=>Math.abs(t[1])-Math.abs(e[1]));let f=d.slice(0,6).map(([e,t])=>({name:e,value:r[e],contribution:t}));return{url:n.original,finalScore:A(l),band:k(l),mlProbability:o,heuristicScore:a.score,features:r,contributions:f,reasons:[...a.hits]}}var M={url_length:`URL length`,hostname_length:`Hostname length`,path_length:`Path length`,num_dots:`Dots in URL`,num_hyphens:`Hyphens in hostname`,num_subdomains:`Subdomain count`,has_ip_host:`Host is a raw IP`,has_at_symbol:`Contains '@'`,num_query_params:`Query parameters`,has_punycode:`Punycode ('xn--')`,has_homograph:`Non-ASCII host chars`,is_https:`Uses HTTPS`,num_digits_in_host:`Digits in hostname`,digit_ratio_host:`Digit ratio of host`,suspicious_tld:`Suspicious TLD`,is_shortener:`URL shortener`,num_suspicious_keywords:`Phishing keywords`,has_double_slash_in_path:`'//' in path`,num_special_chars:`Special characters`,tld_length:`TLD length`},N=new Set([`has_ip_host`,`has_at_symbol`,`has_punycode`,`has_homograph`,`is_https`,`suspicious_tld`,`is_shortener`,`has_double_slash_in_path`]),P=new Set([`has_ip_host`,`has_at_symbol`,`has_punycode`,`has_homograph`,`suspicious_tld`,`is_shortener`,`has_double_slash_in_path`]);function F(e){return e===`Dangerous`?`danger`:e===`Suspicious`?`warn`:`safe`}function I(e){return e===`Dangerous`?`Strong phishing signals detected. Do not enter credentials or personal data.`:e===`Suspicious`?`Some risky traits found. Treat this link with caution and verify the source.`:`No strong phishing signals found. Always stay alert regardless.`}function L(e){let t=document.createElement(`div`);return t.textContent=e,t.innerHTML}function R(e,t){return e===`digit_ratio_host`?t.toFixed(3):Number.isInteger(t)?String(t):t.toFixed(2)}function z(e){let t=F(e.band);return`
    <div class="verdict verdict--${t}" data-testid="verdict">
      <div class="verdict__score" data-testid="verdict-score">${e.finalScore.toFixed(0)}</div>
      <div class="verdict__body">
        <div class="verdict__label" data-testid="verdict-band">${e.band}</div>
        <div class="verdict__detail">${L(I(e.band))}</div>
      </div>
    </div>
    <div class="meter" aria-hidden="true">
      <div class="meter__fill meter__fill--${t}" style="width:${e.finalScore}%"></div>
    </div>
    <div class="score-split">
      <div class="stat">
        <div class="stat__value">${(e.mlProbability*100).toFixed(1)}%</div>
        <div class="stat__label">ML model phishing probability</div>
      </div>
      <div class="stat">
        <div class="stat__value">${e.heuristicScore}/100</div>
        <div class="stat__label">Heuristic rule score</div>
      </div>
    </div>
  `}function B(e){return e.reasons.length===0?`
      <ul class="reasons" data-testid="reasons">
        <li><span class="dot dot--safe"></span><span>No heuristic rules were triggered by this URL.</span></li>
      </ul>
    `:`<ul class="reasons" data-testid="reasons">${e.reasons.map(e=>`
      <li>
        <span class="dot dot--${e.severity===`high`?`danger`:e.severity===`medium`?`warn`:`safe`}"></span>
        <span><strong>+${e.points}</strong> &nbsp;${L(e.reason)}</span>
      </li>`).join(``)}</ul>`}function V(e){return`
    <div class="feature-table-wrap">
      <table class="data-table" data-testid="feature-table">
        <thead><tr><th>Feature</th><th>Value</th></tr></thead>
        <tbody>${c.map(t=>{let n=e[t],r;if(N.has(t)){let e=n>=1;r=`<td class="feat-flag ${(P.has(t)?e:!e)?`flag-yes`:`flag-no`}">${e?`Yes`:`No`}</td>`}else r=`<td class="mono">${R(t,n)}</td>`;return`<tr><td>${L(M[t])}</td>${r}</tr>`}).join(``)}</tbody>
      </table>
    </div>
  `}function H(e){let t=Math.max(.001,...e.map(e=>Math.abs(e.contribution)));return`<div class="contrib" data-testid="contributions">${e.map(e=>{let n=e.contribution>=0,r=Math.abs(e.contribution)/t*50,i=n?`<div class="contrib__bar contrib__bar--pos" style="width:${r}%"></div>`:`<div class="contrib__bar contrib__bar--neg" style="width:${r}%"></div>`,a=n?`+`:`-`;return`
        <div class="contrib__row">
          <div class="contrib__name" title="${L(e.name)}">${L(M[e.name]??e.name)}</div>
          <div class="contrib__track">${i}</div>
          <div class="contrib__value contrib__value--${n?`pos`:`neg`}">${a}${Math.abs(e.contribution).toFixed(2)}</div>
        </div>`}).join(``)}</div>`}var U=`https://github.com/Sarthakagrwal/phishing-url-detector`,W=[{url:`https://github.com/anthropics`,kind:`safe`,label:`github.com`},{url:`https://en.wikipedia.org/wiki/Phishing`,kind:`safe`,label:`wikipedia.org`},{url:`http://secure-login-update-account.verify-paypal.gq/webscr`,kind:`danger`,label:`fake PayPal login`},{url:`http://192.168.10.4/account/verify.php`,kind:`danger`,label:`raw-IP login page`},{url:`http://www.apple.com@198.51.100.23/signin`,kind:`danger`,label:`'@' redirect trick`},{url:`http://xn--pple-43d.com/id/login`,kind:`danger`,label:`punycode look-alike`}];function G(){let t=e.metrics;return`
    <header class="site-header">
      <div class="wrap site-header__inner">
        <div class="site-header__brand">
          <span class="logo">P</span>
          <span>PhishGuard</span>
        </div>
        <nav class="site-header__nav">
          <a href="#how">How it works</a>
          <a href="${U}" target="_blank" rel="noopener">GitHub</a>
        </nav>
      </div>
    </header>

    <main class="wrap">
      <section class="hero">
        <span class="eyebrow">URL phishing analysis</span>
        <h1>Is this link safe to click?</h1>
        <p class="lede">
          Paste any URL below. PhishGuard scores its phishing risk by combining
          transparent heuristic rules with a logistic-regression model trained
          on ~235k real URLs &mdash; running entirely in your browser.
        </p>
      </section>

      <section class="card card--pad-lg">
        <div class="field">
          <label for="url-input">URL to analyse</label>
          <div class="analyze-form">
            <input
              id="url-input"
              type="text"
              class="input--mono"
              placeholder="https://example.com/login"
              autocomplete="off"
              autocapitalize="off"
              spellcheck="false"
            />
            <button id="analyze-btn" class="btn btn--primary" type="button">
              Analyse URL
            </button>
          </div>
          <div class="form-error" id="form-error" role="alert"></div>
        </div>

        <div class="examples mt-3">
          <span class="examples__label">Try an example:</span>
          ${W.map(e=>`<button class="chip chip--${e.kind}" data-url="${e.url}" type="button">${e.label}</button>`).join(``)}
        </div>

        <div class="privacy-note mt-4">
          <span aria-hidden="true">&#128274;</span>
          <span>
            <strong>Private by design.</strong> The URL is analysed entirely on
            your device. Nothing is sent to any server &mdash; there are zero
            network requests after the page loads.
          </span>
        </div>
      </section>

      <section id="results" class="mt-5" hidden>
        <div class="card card--pad-lg">
          <div class="card__title">Verdict</div>
          <div class="verdict-block" id="verdict-area"></div>
        </div>

        <div class="grid grid-2">
          <div class="card">
            <div class="card__title">Why &mdash; heuristic signals</div>
            <div id="reasons-area"></div>
          </div>
          <div class="card">
            <div class="card__title">Top model contributions</div>
            <p class="muted" style="font-size:0.82rem;margin-bottom:var(--sp-3)">
              How each feature pushed the model. Red bars push toward phishing,
              green toward safe.
            </p>
            <div id="contrib-area"></div>
          </div>
        </div>

        <div class="card">
          <div class="card__title">Full feature breakdown (20 string-only features)</div>
          <div id="feature-area"></div>
        </div>
      </section>

      <section id="how" class="card card--pad-lg mt-5">
        <h2>How it works</h2>
        <p class="muted mt-3" style="margin-bottom:var(--sp-4)">
          PhishGuard never visits the URL or resolves DNS. Every signal is
          derived from the URL <em>string</em> alone, which is what lets the
          identical analysis run offline in your browser.
        </p>
        <ol class="how-list">
          <li>
            <span>
              <strong>Parse.</strong> A deterministic parser splits the URL into
              scheme, host, path and query &mdash; matching the Python
              implementation exactly so results are identical everywhere.
            </span>
          </li>
          <li>
            <span>
              <strong>Extract 20 features.</strong> Length, dots, hyphens,
              subdomain depth, raw-IP host, <code>@</code> tricks, Punycode,
              suspicious TLDs, shorteners, phishing keywords and more.
            </span>
          </li>
          <li>
            <span>
              <strong>Run the heuristics.</strong> A transparent rule engine
              flags known phishing tells and explains each one in plain English.
            </span>
          </li>
          <li>
            <span>
              <strong>Run the model.</strong> A logistic-regression classifier
              &mdash; the same model as the Python CLI, evaluated here as a
              closed-form sigmoid &mdash; estimates a phishing probability.
            </span>
          </li>
          <li>
            <span>
              <strong>Blend.</strong> The final score is 60% model + 40%
              heuristics, mapped to a Safe / Suspicious / Dangerous band.
            </span>
          </li>
        </ol>
      </section>
    </main>

    <footer class="site-footer">
      <div class="wrap">
        <p>
          Built by Sarthak Aggarwal as part of a cybersecurity learning portfolio.
          Model: logistic regression &mdash; held-out accuracy
          ${(t.accuracy*100).toFixed(1)}%, precision
          ${(t.precision*100).toFixed(1)}%, recall
          ${(t.recall*100).toFixed(1)}%, ROC-AUC ${t.roc_auc.toFixed(3)}.
        </p>
        <p class="mt-3">
          Trained on the
          <a
            href="https://archive.ics.uci.edu/dataset/967/phiusiil+phishing+url+dataset"
            target="_blank"
            rel="noopener"
            >PhiUSIIL Phishing URL Dataset</a
          >
          (UCI Machine Learning Repository, CC BY 4.0). PhishGuard is an
          educational tool and not a guarantee of safety.
        </p>
      </div>
    </footer>
  `}function K(e){let t=document.getElementById(`form-error`),n=document.getElementById(`results`),r=e.trim();if(r===``)return t.textContent=`Please enter a URL to analyse.`,n.hidden=!0,!1;if(/\s/.test(r))return t.textContent=`A URL cannot contain spaces — please check the input.`,n.hidden=!0,!1;t.textContent=``;let i=j(r);return document.getElementById(`verdict-area`).innerHTML=z(i),document.getElementById(`reasons-area`).innerHTML=B(i),document.getElementById(`contrib-area`).innerHTML=H(i.contributions),document.getElementById(`feature-area`).innerHTML=V(i.features),n.hidden=!1,n.scrollIntoView({behavior:`smooth`,block:`nearest`}),!0}function q(){let e=document.getElementById(`app`);if(e===null)throw Error(`PhishGuard: #app mount point not found.`);e.innerHTML=G();let t=document.getElementById(`url-input`);document.getElementById(`analyze-btn`).addEventListener(`click`,()=>{K(t.value)}),t.addEventListener(`keydown`,e=>{e.key===`Enter`&&(e.preventDefault(),K(t.value))});for(let e of document.querySelectorAll(`.chip`))e.addEventListener(`click`,()=>{let n=e.dataset.url??``;t.value=n,K(n)})}q();