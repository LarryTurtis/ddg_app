import { useState } from "react";
import Loader from "./Loader";

function App() {
  let [formData, setFormData] = useState({
    comment: "",
    isPositive: true,
    userId: crypto.randomUUID(),
  });

  let defaultState = {
    success: false,
    error: false,
    isLoading: false,
  };

  let [formState, setFormState] = useState(defaultState);

  const updateForm = (key, value) => {
    setFormData({ ...formData, [key]: value });
  };

  async function handleSubmit(e) {
    setFormState({ ...defaultState, isLoading: true });
    e.preventDefault();
    // async request which may result error
    try {
      const response = await fetch(`/submit`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
      if (!response.ok) {
        throw new Error(await response.json());
      }
      setFormState({ success: true, error: false, isLoading: false });
    } catch (err) {
      // handle your error
      console.error(JSON.stringify(err));
      setFormState({ success: false, error: true, isLoading: false });
    }
  }

  const form = (
    <form onSubmit={handleSubmit}>
      {formState.error ? (
        <div className="err">
          Uh-oh! Something went wrong! Please try again later.
        </div>
      ) : (
        ""
      )}
      {formState.success ? (
        <div className="success">Success! Thank you!</div>
      ) : (
        ""
      )}
      <div className="section">
        <section>
          <label className="btn btn-default">
            We want your feedback!
            <div>
              <textarea
                name="comment"
                type="textArea"
                rows="5"
                columns="70"
                value={formData.comment}
                onChange={(e) => updateForm(e.target.name, e.target.value)}
                disabled={formState.success || formState.error}
              />
            </div>
          </label>
        </section>
        <section>
          <label className="btn btn-default">
            How was your experience?
            <div>
              <select
                name="comment"
                value={formData.isPositive}
                onChange={(e) => updateForm(e.target.name, e.target.value)}
                disabled={formState.success || formState.error}
              >
                <option value="true">Positive</option>
                <option value="false">Negative</option>
              </select>
            </div>
          </label>
        </section>
        <section>
          <input
            type="submit"
            value="Submit"
            disabled={formState.success || formState.error}
          />
        </section>
      </div>
    </form>
  );
  return (
    <div className="App-content">{formData.isLoading ? <Loader /> : form}</div>
  );
}

export default App;
