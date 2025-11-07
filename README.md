# LLM 이란?
LLM (Large Language Model) 은 **대규모 언어 모델** 아다  
즉, 사람의 언어를 이해하고 생성하도록 훈련된 인공지능 모델

>인공지능 (AI)  
└─ 머신러닝 (Machine Learning)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**데이터에서 패턴을 학습해 예측하거나 분류하는 기술**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─ 딥러닝 (Deep Learning)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**머신러닝의 하위 개념으로, 인공신경망(Neural Network) 을 사용해 데이터를 학습**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─ LLM (Large Language Model)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**딥러닝 중에서도 자연어 처리(NLP) 분야의 거대 신경망 모델**  


## 왜 혼동이 생기냐?
일부 사람은 LLM을 **머신러닝 모델** 이라고 한다  
→ 틀린 건 아니지만, 너무 포괄적

### **머신러닝 ⊃ 딥러닝 ⊃ LLM**

>LLM은 **딥러닝 기반의 대규모 언어 모델**이다.  
따라서 머신러닝이라고 해도 틀리진 않지만, 정확히는 딥러닝 모델이다.


⸻

1. Embedding (임베딩)

문장을 숫자로 바꾸는 기술

	사람이 이해하는 텍스트(단어, 문장) 는 컴퓨터가 바로 이해하지 못합니다.
	따라서 이를 숫자의 벡터 형태로 변환해 저장하는 과정을 Embedding이라 합니다.

ex ::  
"사과" → [0.2, 0.7, 0.1, ...]  
"바나나" → [0.3, 0.6, 0.2, ...]


→ 비슷한 의미를 가진 단어일수록 벡터 공간에서 가까운 위치에 존재합니다.  
→ 예: “사과”와 “배”는 가깝고, “자동차”는 멀리 떨어져 있음.

📌 요약:
텍스트를 숫자로 변환해, 컴퓨터가 단어의 ‘의미’를 이해할 수 있도록 하는 기술.

⸻

2. Retrieval (리트리벌)

필요한 문서를 똑똑하게 찾아오는 과정

“FALLETTER 프로젝트가 뭐야?” 라는 질문을 받았을 때,  
LLM은 모든 정보를 직접 기억하지 않고,  
저장된 문서 중 관련된 내용을 찾아오는 단계를 거친다.

이때 문서와 질문 모두 Embedding으로 벡터화되어 있으며,  
LLM은 이 벡터 간의 유사도를 계산해 가장 비슷한 문서를 찾아낸다.

📌 요약:  
사용자의 질문과 의미적으로 가장 비슷한 문서를 찾아주는 검색 과정.  
(= 의미 기반 검색)

⸻

3. Short-term Memory vs Long-term Memory

AI의 두 가지 기억 구조

Short-term Memory (단기 기억)

→ 현재 대화 세션 동안만 유지되는 정보
세션이 종료되면 함께 사라진다.

Long-term Memory (장기 기억)

→ 지속적으로 저장되어 이후에도 활용 가능한 정보  
다음 대화에서도 이 정보를 활용할 수 있다.

📌 요약:  
단기 기억 = 현재 대화 중심  
장기 기억 = 지속적으로 누적·활용되는 정보

⸻  
Embedding으로 문장을 숫자로 변환하고, Retrieval로 관련 문서를 찾아내며,  
Short-term과 Long-term Memory를 나눠 관리함으로써  
‘문서 기반 Q&A + 지속적 학습형 AI’ 구현이 가능해집니다.  
